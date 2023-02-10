def call(Map args = [
	              gitBranch: null,
                      gitRepo: null,
                      gitCredentialsId: null 
                     ] ) {	
	
// 	def gitRepo = 'https://github.com/nitinpainoli/TerraformGCP.git'
//         def gitCredentialsId = 'mycredsnitin'
//         def gitBranch = 'main' 
		

// def call(Map config = [:]) {
	
// 	        def gitRepo = "${gitRepo}"
//                 def gitCredentialsId = "${gitCredentialsId}"
//                 def gitBranch = "${gitBranch}"
	

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
    
        stage("Terraform init") {
				
				steps {
					
				    sh "terraform init -input=false"

					
				}
			} // Stage End
    }
}
}
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

