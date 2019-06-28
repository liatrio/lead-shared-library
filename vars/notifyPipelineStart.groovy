import groovy.json.JsonOutput

def call(params) {

    if (!params) params = [:]

    def request = params.request ? params.request : [:]

    request.type = 'pipeline-start'
    request.gitUrl = env.GIT_URL
    request.gitCommit = env.GIT_COMMIT
    request.buildId = env.BUILD_ID

    request.branch = env.BRANCH_NAME

    def Jenkinsfile = params.Jenkinsfile ? params.Jenkinsfile : 'Jenkinsfile'
    def pipeline = readFile file: Jenkinsfile
    request.stageNames = getStageNames(pipeline)

    def requestBody = JsonOutput.toJson(request)
    def url = 'http://operator-jenkins.' + env.toolchainNamespace + '.svc.cluster.local:3000/pipeline-status';

    def response = httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'POST', requestBody: requestBody, url: url
    println('Response: (' + response.status + ') ' + response.content)
}

def getStageNames(pipeline){

  def names = []
  def lines = pipeline.readLines()

  for (int i = 0; i < lines.size(); i++){
    def line = lines[i]
    if (line.trim().size() == 0){}
    else {
      if (line.contains("stage(\'")){
        String [] tokens = line.split("\'");
        String stage = tokens[1];
        names.add(stage)
      }
      else if (line.contains("stage(\"")){
        String [] tokens = line.split("\"");
        String stage = tokens[1];
        names.add(stage)
      }
    }
  }

  return names
}
