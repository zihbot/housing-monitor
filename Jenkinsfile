#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage("Build") {
            sh "docker-compose build"
        }
        stage("Deploy") {
            steps {
                sh "docker stop $(docker ps -a -q)"
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