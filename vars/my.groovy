	
//def call(Map config = [:]) {

// def call(Map args = [
//                       dir: '.',
//                       args: ''
//                      ] ){
	
// 	  args.dir = args.dir ?: '.'


// def call(Map config = [:]) {


def call() {
	
def TERRAFORM_DIR = "/home/ec2-user/jenkins/workspace/abc"
	
pipeline {
    agent any
	
//    environment {
//       TERRAFORM_DIR = "$args.dir"
//    }
    stages {
     	stage("Clean Workspace") {
				steps {
					cleanWs()
				}
			}
   
        stage("Terraform init") {
		  steps {
			  	    sh "cd $TERRAFORM_DIR"	
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

