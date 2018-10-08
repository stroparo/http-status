#!/usr/bin/env groovy

project_name = 'httpstatus'
container_image_name = 'stroparo/httpstatus'
docker_host_port = 80
docker_container_port = 4321

pipeline {

    agent none

    options {
        buildDiscarder(
            logRotator(
                numToKeepStr: '10',
            )
        )
    }

    environment {
        POD_NAME = 'some_pod' // TODO
    }

    stages {

        stage("Build ${project_name} app") {
            agent any
            steps {
                cleanWs()
                checkout scm
                sh './gradlew build installDist'
            }
        }

        stage('Build ${project_name} container') {
            agent any
            steps {
                sh "docker build -f Dockerfile -t ${container_image_name} ."
            }
        }

        stage('Deploy docker container') {
            agent any
            steps {
                echo "Using Docker image '${container_image_name}'"
                echo "Removing running instances"

                // TODO Refactor this to handle it efficiently

                sh "docker stop ${project_name} || true"
                sh "docker rm ${project_name} || true"
                sh "!(docker ps | grep -q ${project_name})"
                sh "docker run -d --name ${project_name} -p ${docker_host_port}:${docker_container_port} ${container_image_name}"
            }
        }

    }
}

