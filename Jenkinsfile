#!/usr/bin/env groovy

project_name = 'http-status'
container_image_name = 'stroparo/http-status'
docker_host_port = 80
docker_container_port = 8080

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

        stage("Build app") {
            agent any
            steps {
                cleanWs()
                checkout scm
                sh './gradlew build installDist'
            }
        }

        stage('Build container') {
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

                sh "docker stop ${project_name} || true"
                sh "docker rm ${project_name} || true"
                sh "!(docker ps | grep -q ${project_name})"
                sh "docker run -d --name ${project_name} -p ${docker_host_port}:${docker_container_port} ${container_image_name}"
            }
        }

    }

    post {
      always {
        archiveArtifacts artifacts: 'buildS'
      }
    }
}

