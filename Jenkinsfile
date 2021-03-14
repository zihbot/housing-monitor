#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage("Compose up") {
            steps {
                sh "docker-compose up -d"
                sh "docker-compose logs -t --tail 300"
            }
        }
        stage("Cleanup") {
            steps {
                sh "docker image prune -f"
                sh "docker image prune -f --filter label=stage=intermediate"
            }
        }
    }
}