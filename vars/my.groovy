def call(Map args =[ jenkins_agent: '', tf_dir: '', tf_workspace: ''] ){

  args.tf_dir = args.tf_dir ?: '.'
  args.jenkins_agent = args.jenkins_agent ?: 'master'
  args.tf_workspace = args.tf_workspace ?: 'default'	

pipeline {
    agent {
	    label "${args.jenkins_agent}"
    }
 
    environment {
      TERRAFORM_DIR = "$args.tf_dir"
	 }	
	
    stages {
	    
	    
     	stage("Clean Workspace") {
				steps {
					cleanWs()
				}
			}
   
        stage("Terraform init") {
		  steps {
			  dir("${TERRAFORM_DIR}") {

			           sh "ls -la"
				    sh "terraform init -input=false"	
				}
			  
			} 
	     }
				
        stage('Terraform-Format') {
            steps {
		 dir("${TERRAFORM_DIR}") {  
                sh "terraform fmt -list=true -diff=true"
		 }
            }
            }

        stage('Terraform-Validate') {
            steps {
	        dir("${TERRAFORM_DIR}") {    
                sh "terraform validate"
		}
            }
            }

        stage('Terraform-Plan') {
            steps {
		dir("${TERRAFORM_DIR}") {	    
                sh 'terraform plan -out tfplan'
		}
            }
            }

        stage('Terraform-Approval') {
            steps {
	        dir("${TERRAFORM_DIR}") {		    
                script {
                timeout(time: 10, unit: 'MINUTES') {
                    def userInput = input(id: 'Approve', message: 'Do You Want To Apply The Terraform Changes?', parameters: [
                    [$class: 'BooleanParameterDefinition', defaultValue: false, description: 'Apply Terraform Changes', name: 'Approve?']
                    ])
                }
                }
            }
            }
	}

//         stage('Terraform-Apply') {
//             steps {
//                 sh 'terraform apply -input=false tfplan'
                
//             }
//             }

        }
    }	
}

