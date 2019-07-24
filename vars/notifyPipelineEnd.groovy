import groovy.json.JsonOutput

def call(params) {

    if (!params) params = [:]

    def request = params.request ? params.request : [:]

    request.type = 'pipeline-end'
    request.product = env.product
    request.gitUrl = env.GIT_URL
    request.gitCommit = env.GIT_COMMIT
    request.buildId = env.BUILD_ID
    request.result = params.result ? params.result : 'success'

    def requestBody = JsonOutput.toJson(request)
    sendRequest(requestBody)
}
