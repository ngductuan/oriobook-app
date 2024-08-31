pipeline {
    agent {
        label 'lab-server'
    }
    environment {
        APP_NAME = 'oriobook'
        DOCKERHUB_CREDENTIALS_ID = 'docker-to-jenkins-pat'
        DOCKER_REPO_BASE = "ngductuan/${APP_NAME}"

        DOCKER_IMAGE_FE = ''
        DOCKER_IMAGE_BE = ''
        GIT_COMMIT = ''

        FE_FOLDER = "oriobook-frontend"
        BE_FOLDER = "oriobook-backend"

        IMAGE_TAG = ''
    }
    stages {
        stage('cleanup') {
            steps {
                deleteDir()
            }
        }
        stage('clone') {
            steps {
                script {
                    git branch: 'develop', credentialsId: 'jenkins-github-user-repo', url: 'https://github.com/ngductuan/oriobook-app.git'
                    
                    GIT_COMMIT = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()

                    TAG_NAME = sh(returnStdout: true, script: "git tag --points-at HEAD").trim()
                    IMAGE_TAG = "${TAG_NAME ?: 'build'}"
                    
                    DOCKER_IMAGE_FE = "${DOCKER_REPO_BASE}-fe:${IMAGE_TAG}-${GIT_COMMIT}"
                    DOCKER_IMAGE_BE = "${DOCKER_REPO_BASE}-be:${IMAGE_TAG}-${GIT_COMMIT}"
                }
            }
        }
        stage('build_and_push') {
            steps {
                script {
                    withDockerRegistry(credentialsId: DOCKERHUB_CREDENTIALS_ID, url: 'https://index.docker.io/v1/') {

                        echo "Frontend Docker Image: ${DOCKER_IMAGE_FE}"
                        echo "Backend Docker Image: ${DOCKER_IMAGE_BE}"

                        sh "docker build -t ${DOCKER_IMAGE_FE} ${FE_FOLDER}"
                        sh "docker build -t ${DOCKER_IMAGE_BE} ${BE_FOLDER}"

                        sh "docker push ${DOCKER_IMAGE_FE}"
                        sh "docker push ${DOCKER_IMAGE_BE}"

                        // Remove the Docker images after pushing
                        sh "docker rmi ${DOCKER_IMAGE_FE} || true"
                        sh "docker rmi ${DOCKER_IMAGE_BE} || true"
                    }
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    try {
                        timeout(time: 5, unit: 'MINUTES'){
                            env.useChoice = input message: "Can it be deployed?",
                            parameters: [choice(name: 'deploy', choices: 'no\nyes', description: 'Choose "yes" if you want to deploy')]
                        }
                        if (env.useChoice == 'yes') {
                            withDockerRegistry(credentialsId: DOCKERHUB_CREDENTIALS_ID, url: 'https://index.docker.io/v1/') {
                                withCredentials([file(credentialsId: 'FE_ENV', variable: 'FE_ENV_PATH')]) {
                                    // Check if the container 'orio-fe' exists
                                    def feContainerExists = sh(script: "docker ps -a --filter 'name=orio-fe' --format '{{.Names}}' | grep -w orio-fe || true", returnStdout: true).trim()

                                    if (feContainerExists) {
                                        // If the container exists, remove it
                                        echo "Removing the existing container 'orio-fe'"

                                        sh """
                                            docker rm -f orio-fe
                                            docker images --filter=reference='ngductuan/oriobook-fe:*' --format "{{.ID}}" | xargs --no-run-if-empty docker rmi -f
                                        """
                                    }

                                    // Pull the new image and run the container
                                    sh """
                                        docker pull ${DOCKER_IMAGE_FE}
                                        docker run --name orio-fe --env-file \$FE_ENV_PATH -dp 5001:80 ${DOCKER_IMAGE_FE}
                                    """
                                }


                                withCredentials([file(credentialsId: 'BE_ENV', variable: 'BE_ENV_PATH')]) {
                                    // Check if the container 'orio-be' exists
                                    def beContainerExists = sh(script: "docker ps -a --filter 'name=orio-be' --format '{{.Names}}' | grep -w orio-be || true", returnStdout: true).trim()

                                    if (beContainerExists) {
                                        // If the container exists, remove it
                                        echo "Removing the existing container 'orio-be'"

                                        sh """
                                            docker rm -f orio-be
                                            docker images --filter=reference='ngductuan/oriobook-be:*' --format "{{.ID}}" | xargs --no-run-if-empty docker rmi -f
                                        """
                                    }

                                    // Pull the new image and run the container
                                    sh """
                                        docker pull ${DOCKER_IMAGE_BE}
                                        docker run --name orio-be --env-file \$BE_ENV_PATH -dp 5002:8080 ${DOCKER_IMAGE_BE}
                                    """
                                }

                            }
                        } else {
                            echo "Deployment is skipped"
                        }
                    } catch (Exception err){
                        echo "Timeout to deploy or error occurred: ${err}"
                    }
                }
            }
        }
    }
}
