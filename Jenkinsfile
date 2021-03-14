#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage("Compose up") {
            steps {
                sh "docker-compose up -d"
            }
        }
        stage("Cleanup") {
            steps {
                sh "docker image prune"
                sh "docker image prune --filter 'stage=intermediate'"
            }
        }
    }
}