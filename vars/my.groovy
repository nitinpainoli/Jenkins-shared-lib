// def call(Map args = [
//                       gitRepo: '',
//                       gitBranch: '.',
//                       gitCredentialsId: ''
//                      ] ){

//   args.gitRepo = args.gitRepo ?: ''
//   args.gitBranch = args.gitBranch ?: ''
//   args.gitCredentialsId = args.gitCredentialsId ?: '' 

//def call(Map args = [] ) {
	
def call(Map config = [:]) {
	
pipeline {
    agent any
//     environment {
            
//             gitRepo = "$args.gitRepo"
//             gitBranch = "$args.gitBranch"
//             gitCredentialsId = "$args.gitCredentialsId"



//     }
    stages {
     	stage("Clean Workspace") {
				steps {
					cleanWs()
				}
			}

//         stage("Git checkout & Initialize for Build") {
// 		   steps {
// 					script {
						
// 				          	git(url: gitRepo, branch: gitBranch, credentialsId: gitCredentialsId)
//   					}
// 				}
// 			}    
        stage("Terraform init") {
		  steps {
			  	    sh "cd ${config.terraform-dir}	
				    sh "terraform init -input=false"	
				}
			} 		
				
        }
        stage('Terraform-Format') {
            steps {
                sh "terraform fmt -list=true -diff=true"
                
            }
            }

        stage('Terraform-Validate') {
            steps {
                sh "terraform validate"
                
            }
            }

        stage('Terraform-Plan') {
            steps {
                sh 'terraform plan -out tfplan'
                
            }
            }

        stage('Terraform-Approval') {
            steps {
                script {
                timeout(time: 10, unit: 'MINUTES') {
                    def userInput = input(id: 'Approve', message: 'Do You Want To Apply The Terraform Changes?', parameters: [
                    [$class: 'BooleanParameterDefinition', defaultValue: false, description: 'Apply Terraform Changes', name: 'Approve?']
                    ])
                }
                }
            }
            }

        stage('Terraform-Apply') {
            steps {
                sh 'terraform apply -input=false tfplan'
                
            }
            }

        }
}

