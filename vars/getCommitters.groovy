def call() {
  def log = sh(returnStdout: true, script: "git --no-pager log -5")

//  def log = "git --no-pager log -5".execute()
//  log.waitFor()
  println "printing log below:"
  println log
  def emailLog  = log =~ /<(.*@.*)>/
  List<String> userEmailList = new ArrayList<>()
  for(email in emailLog){
    userEmailList << email[1]
  }
  println "printing userEmailList: "
  println userEmailList
  return userEmailList
}
