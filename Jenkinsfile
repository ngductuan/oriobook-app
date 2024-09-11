pipeline {
    agent {
        label 'lab-server'
    }
    environment {
        APP_NAME = 'oriobook'
        DOCKERHUB_CREDENTIALS_ID = 'docker-to-jenkins-pat'
        DOCKER_REPO_BASE = "ngductuan/${APP_NAME}"

        FE_FOLDER = "oriobook-frontend"
        BE_FOLDER = "oriobook-backend"

        GIT_COMMIT = ''
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
                    IMAGE_TAG = "${TAG_NAME ?: 'build'}-${GIT_COMMIT}"

                    // Copy .env files to the project folders
                    withCredentials([file(credentialsId: 'FE_ENV', variable: 'FE_ENV_PATH')]) {
                        sh "cp \$FE_ENV_PATH ${FE_FOLDER}/.env"
                    }
                    
                    withCredentials([file(credentialsId: 'BE_ENV', variable: 'BE_ENV_PATH')]) {
                        sh "cp \$BE_ENV_PATH ${BE_FOLDER}/.env"
                    }
                }
            }
        }
        stage('build_and_push') {
            steps {
                script {
                    withDockerRegistry(credentialsId: DOCKERHUB_CREDENTIALS_ID, url: 'https://index.docker.io/v1/') {
                        // Build and push images
                        sh """
                            IMAGE_TAG=${IMAGE_TAG} docker-compose -f docker-compose.yml build
                            IMAGE_TAG=${IMAGE_TAG} docker-compose -f docker-compose.yml push
                        """
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
                                // Pull images and deploy containers
                                sh """
                                    IMAGE_TAG=${IMAGE_TAG} docker-compose -f docker-compose.yml down
                                    IMAGE_TAG=${IMAGE_TAG} docker-compose -f docker-compose.yml up -d
                                """
                            }
                        } else {
                            echo "Deployment is skipped"
                        }
                    } catch (Exception err) {
                        echo "Timeout to deploy or error occurred: ${err}"
                    }
                }
            }
        }
    }
}
