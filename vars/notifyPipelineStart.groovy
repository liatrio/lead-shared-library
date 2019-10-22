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

    request.committerList = getCommitters()

    def requestBody = JsonOutput.toJson(request)
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

def getCommitters(){
  log = "git log -n 5".execute()
  def emailLog  = log.text =~ /<(.*@.*)>/
  List<String> userEmailList = new ArrayList<>()
  for(email in emailLog){
    userEmailList << email[1]
  }
  return userEmailList
}
