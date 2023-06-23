pipeline {
    agent any

    //Se define el archivo para realizar los escaneos de SonarQube
    //De preferencia es una configuracion realizada en Jenkins
    environment {
        scannerHome=tool "sonarqube-scanner"
        NEXUS_VERSION="nexus3"
        NEXUS_PROTOCOL="http"
        NEXUS_URL="127.0.0.1:8088"
        NEXUS_REPOSITORY="maven-alan-repo"
        NEXUS_CREDENTIAL_ID="nexus-user-credentials"
        //IMAGE_TEST="alankairosds/msvc-catalogo"
    }

    //Se define la version que se utilizara de Maven para construir el proyecto
    //De preferencia es una configuracion realizada en Jenkins
    tools {
        maven "jenkins-maven"
    }

    //Se definen todos los pasos que realizara el pipeline al momento de su ejecucion
    stages {
        //Paso que realiza la construccion del proyecto limpiando previamente las
        //carpeta de construcciones pasadas, saltandose la ejecucion de Tests
        stage('Build Project') {
            steps {
                sh "mvn -B -DskipTests clean package"
            }
        }

        //Paso que realiza la ejecucion de los Test del proyecto
        stage('Run Tests'){
            steps {
                sh "mvn test"
            }
        }

        //Paso donde se realiza el escaneo del proyecto con SonarQube
        //Las propiedades para realizar el analisis las toma del archivo sonar-project.properties
        stage('SonarQube') {
            steps {
                withSonarQubeEnv("sonarqube-container") {
                    //sh "${scannerHome}/bin/sonar-scanner" - LINUX
                    sh "${scannerHome}/bin/sonar-scanner.bat" //WINDOWS
                }
            }
        }

        //Paso para guardar el reporte de covertura de jacoco en Jenkins
        stage('Coverage') {
            steps {
                recordCoverage(tools: [[pattern: '**/jacoco.xml']])
            }
        }

        //Paso donde almacenara de forma temporal en Jenkins los artefactos
        //que se encuentran en todas las carpetas target del proyecto
        stage('Save Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        //Paso para publicar los artefactos en el repositorio de dependencias NEXUS
        stage('Publish Artifacts') {
            steps {
                script {
                    pom_parent = readMavenPom file: "pom.xml";
                    nexusArtifactUploader (
                        nexusVersion: NEXUS_VERSION,
                        protocol: NEXUS_PROTOCOL,
                        nexusUrl: NEXUS_URL,
                        groupId: pom_parent.groupId,
                        version: pom_parent.version,
                        repository: NEXUS_REPOSITORY,
                        credentialsId: NEXUS_CREDENTIAL_ID,
                        artifacts: [
                            [
                                artifactId: 'msvc-login',
                                classifier: '',
                                file: './login/target/msvc-login.jar',
                                type: 'jar'
                            ]
                        ]
                    );
                }
            }
        }

        //Paso para realizar la construccion de las imagenes Docker
        /*stage('Build Docker Images') {
            steps {
                sh "docker build -t $IMAGE_TEST . -f ./msvc-catalogo/docker/Dockerfile"
            }
        }

        //Paso para realizar login en Docker Hub
        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_hub_credentials', passwordVariable: 'paw', usernameVariable: 'user')]) {
                    sh 'echo $paw | docker login --username $user --password-stdin'
                }
            }
        }

        //Paso para publicar las imagenes creadas en Docker Hub
        stage('Publish Images') {
            steps {
                sh "docker push $IMAGE_TEST"
            }
        }

        //Paso para eliminar las imagenes creadas de forma local
        stage('Clean Docker Images') {
            steps {
                sh 'docker rmi $IMAGE_TEST:latest'
            }
        }*/
    }
    //Acciones que se realizan despues de que se terminaron de ejecutar los pasos anteriores
    /*post {
        //Estas acciones se ejecutan siempre, sin importar si los pasos anteriores
        //se ejecutaron o no con exito
        always {
            //Realizar Logout de Docker Hub
            sh 'docker logout'
        }
    }*/
}
