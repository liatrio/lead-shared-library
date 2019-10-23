import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def call(params) {
    if (!params) params = [:]

    def request = params.request ? params.request : [:]

    request.type = 'pipeline-start'
    request.product = env.product
    request.url = env.BUILD_URL
    request.buildId = env.BUILD_ID
    request.gitUrl = env.GIT_URL
    request.gitCommit = env.GIT_COMMIT
    request.buildId = env.BUILD_ID

    request.branch = env.BRANCH_NAME

    def Jenkinsfile = params.Jenkinsfile ? params.Jenkinsfile : 'Jenkinsfile'
    def pipeline = readFile file: Jenkinsfile
    request.stageNames = getStageNames(pipeline)

    println request.getClass()

    def tempReq = new JsonSlurper().parseText(request)
    tempReq.put('committers', new JsonBuilder(getCommitters()));
    request = tempReq

    def requestBody = JsonOutput.toJson(request)

    println requestBody
    sendRequest(requestBody)
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


