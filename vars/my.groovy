def call (Map params) {
	
	def gitRepo = null
    def gitCredentialsId = null

pipeline {
    agent any

    stages {
     	stage("Clean Workspace") {
				steps {
					cleanWs()
				}
			}

        stage("Git checkout & Initialize for Build") {
			
				steps {
					script {
						git(url: gitRepo, branch: gitBranch, credentialsId: gitCredentialsId)

					}
				}
			}    
    }
}

	 // Stage End

// 			stage("Terraform init") {
				
// 				steps {
// 					script {
// 						terraform init
// 					}
// 				}
// 			} // Stage End
		
	// 	post {
	// 		always {
	// 			script {
	// 				utils.cleanup()
	// 				cleanWs()
	// 			}
	// 		}
	// 		success {
    //              script {
    //                  if (envName == 'prod')
    //  					  {       
    //   						slackSend channel: "#aws-jenkins",
    //   						botUser: true, 
    //   						tokenCredentialId: "#################", 
    //   						color: 'good', 
    //   						message: "${appName} application Jenkins Job SUCCESS in ${envName} environment. - ${BUILD_URL}"

    // 					 }
    //                    }

    //                 }
		    
	// 		failure {
    //               script {
    //                  if (envName == 'prod')
    //  					  {       
    //   						slackSend channel: "#aws-jenkins",
    //   						botUser: true, 
    //   						tokenCredentialId: "#################", 
    //   						color: 'bad', 
    //   						message: "ALERT: ${appName} application Jenkins Job FAILED in ${envName} environment. - ${BUILD_URL}"

    // 					 }
    //                    }

    // }
	// 	}
	}
