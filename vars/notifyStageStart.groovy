import groovy.json.JsonOutput

def call(params) {

  if (!params) params = [:]

  def request = params.request ? params.request : [:]

  request.type = 'stage-start'
  request.product = env.product
  request.gitUrl = env.GIT_URL
  request.gitCommit = env.GIT_COMMIT
  request.buildId = env.BUILD_ID
  request.branch = env.BRANCH_NAME
  request.stage = env.STAGE_NAME
  request.result = 'inProgress'

  def requestBody = JsonOutput.toJson(request)
  sendRequest(requestBody)
}
