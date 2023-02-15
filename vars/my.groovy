
def call(Map args) {

"${args.agent}: ${args.dir}"	

  pipeline {

    agent {
	    label "${args.agent}"
    }

    stages {

        stage('Terraform Fmt') {
          steps {
                //dir("${args.dir}") {  
		sh "cd "${args.dir}""
		sh "pwd"
			
                sh "terraform fmt -list=true -diff=true"
		 
          }
        }
  

      stage('Terraform Init') {
        steps {
        dir("${args.dir}") {

			           sh "ls -la"
				    sh "terraform init -input=false"	
				}
        }
      }

      stage('Terraform Validate') {
        steps {
          dir("${args.dir}") {    
                sh "terraform validate"
		}
        }
      }

      stage('Terraform Plan') {
        steps {
         dir("${args.dir}") {	    
                sh 'terraform plan -out tfplan'
		}
        }
      }

    }


  }

}
