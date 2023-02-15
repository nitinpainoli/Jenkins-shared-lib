// def call(Map args = [
//                       version: 'latest',
//                       dir: '.',
//                       args: '',
//                       apply_branch_pattern: '^(.*/)?(main|master)$',
//                       env: [],
//                       creds: [],
//                       checkout: [],
//                       container: null,
//                       yamlFile: 'ci/jenkins-pod.yaml'
//                      ] ){
	
def call(Map args =[ agent: '', dir: ''] ){

  args.dir = args.dir ?: '.'
  args.agent = arges.agent ?: 'master'	

//"${args.agent}: ${args.dir}"	

  pipeline {

    agent {
	    label "${args.agent}"
    }

    stages {

        stage('Terraform Fmt') {
          steps {
                dir("${args.dir}") {  
		sh "pwd"
			
                sh "terraform fmt -list=true -diff=true"
		} 
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
