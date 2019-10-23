def call() {
  def log = sh(returnStdout: true, script: "git --no-pager log -5")
  def emailLog  = log =~ /<(.*@.*)>/
  List<String> userEmailList = new ArrayList<>()
  for(email in emailLog){
    userEmailList << email[1]
  }
  return userEmailList
}
