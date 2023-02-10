def call(gitRepo, gitBranch, gitCredentialsId) {
    git(url: gitRepo, branch: gitBranch, credentialsId: gitCredentialsId)
}
