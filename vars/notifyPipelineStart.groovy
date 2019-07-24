import groovy.json.JsonOutput

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

    def requestBody = JsonOutput.toJson(request)
    println('before requestBody here...');
    sendRequest(requestBody)
    println('after requestBody here...');
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
