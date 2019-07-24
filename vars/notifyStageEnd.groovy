import groovy.json.JsonOutput

def call(params) {

  if (!params) params = [:]

  def request = params.request ? params.request : [:]

  request.type = 'stage-end'
  request.product = env.product
  request.gitUrl = env.GIT_URL
  request.gitCommit = env.GIT_COMMIT
  request.buildId = env.BUILD_ID
  request.branch = env.BRANCH_NAME
  request.stage = env.STAGE_NAME
  request.result = params.result ? params.result : 'success'
  request.status = params.status ? params.status : ''

  def requestBody = JsonOutput.toJson(request)
  sendRequest(requestBody)
}
