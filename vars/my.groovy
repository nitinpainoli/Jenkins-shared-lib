
def call(Map args) {

"${args.agent}: ${args.dir}"	

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

    //   stage('Approval') {
    //     when {
    //       beforeInput true
    //       allOf {
    //         expression {
    //           env.TERRAFORM_CHANGES != 'false'
    //         }
    //         anyOf {
    //           // XXX: branch pattern fails to match anything unless part of a multibranch pipeline
    //           branch pattern: "$args.apply_branch_pattern", comparator: "REGEXP"
    //           // which is why we use an expression evaluation here
    //           expression {
    //             (env.GIT_BRANCH =~ ~"$args.apply_branch_pattern").matches()
    //           }
    //         }
    //       }
    //     }
    //     steps {
    //       //approval(args.approval_args)
    //       withEnv(args.env){
    //         approval()
    //       }
    //     }
    //   }

    //   stage('Terraform Apply') {
    //     when {
    //       allOf {
    //         expression {
    //           env.TERRAFORM_CHANGES != 'false'
    //         }
    //         anyOf {
    //           // XXX: branch pattern fails to match anything unless part of a multibranch pipeline
    //           branch pattern: "$args.apply_branch_pattern", comparator: "REGEXP"
    //           // which is why we use an expression evaluation here
    //           expression {
    //             (env.GIT_BRANCH =~ ~"$args.apply_branch_pattern").matches()
    //           }
    //         }
    //       }
    //     }
    //     steps {
    //       withEnv(args.env){
    //         withCredentials(args.creds){
    //           terraformApply()
    //         }
    //       }
    //     }
    //   }

    }


  }

}
