def call () {

    String label = 'Terraform Init'
     script: '''
               terraform init -input=false
             '''
}