pipeline {
    agent {
        label 'lab-server'
    }
    environment {
        APPNAME = 'oriobook'
        DOCKERHUB_CREDENTIALS_ID = 'docker-to-jenkins-pat'
        DOCKER_REPO_BASE = "ngductuan/${APPNAME}"

        DOCKER_IMAGE_FE = ''
        DOCKER_IMAGE_BE = ''
        GIT_COMMIT = ''

        FE_FOLDER = "oriobook-frontend"
        BE_FOLDER = "oriobook-backend"

        IMAGE_TAG = "${env.TAG_NAME ?: 'build'}"
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
                    git branch: 'main', credentialsId: 'jenkins-gitlab-user-account', url: 'http://gitlab.orio-studio.com/ngductuan/oriobook-app'
                    
                    GIT_COMMIT = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    
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
                                    sh """
                                        docker pull ${DOCKER_IMAGE_FE}
                                        docker rm -f orio-fe || true
                                        docker run --name orio-fe --env-file \$FE_ENV_PATH -dp 5001:8080 ${DOCKER_IMAGE_FE}
                                    """
                                }

                                withCredentials([file(credentialsId: 'BE_ENV', variable: 'BE_ENV_PATH')]) {
                                    sh """
                                        docker pull ${DOCKER_IMAGE_BE}
                                        docker rm -f orio-be || true
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
