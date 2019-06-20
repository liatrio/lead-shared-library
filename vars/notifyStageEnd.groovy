import groovy.json.JsonOutput

def call(params) {

  if (!params) params = [:]

  def request = params.request ? params.request : [:]

  request.type = 'stage-end'
  request.gitUrl = env.GIT_URL
  request.gitCommit = env.GIT_COMMIT
  request.buildId = env.BUILD_ID
  request.branch = env.BRANCH_NAME
  request.stage = env.STAGE_NAME
  request.result = params.result ? params.result : 'success'

  def requestBody = JsonOutput.toJson(request)
  def url = (env.jenkinsOperator ? env.jenkinsOperator : "http://localhost:5555/notify-pipeline")

  def response = httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: requestBody, url: url
  println('Response: (' + response.status + ') ' + response.content)
}
