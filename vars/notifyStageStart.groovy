import groovy.json.JsonOutput

def call(params) {

  if (!params) params = [:]

  def request = params.request ? params.request : [:]

  request.type = 'stage-start'
  request.gitUrl = env.GIT_URL
  request.gitCommit = env.GIT_COMMIT
  request.buildId = env.BUILD_ID
  request.branch = env.BRANCH_NAME
  request.stage = env.STAGE_NAME

  def requestBody = JsonOutput.toJson(request)
  def url = 'http://operator-jenkins.' + env.toolchainNamespace + '.svc.cluster.local:3000/pipeline-status';

  def response = httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: requestBody, url: url
  println('Response: (' + response.status + ') ' + response.content)
}
