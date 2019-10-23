def call() {
  def log = "git log -5".execute() | "cat".execute()
  log.waitFor()
  println "printing log below:"
  println log
  println log.text
  def emailLog  = log.text =~ /<(.*@.*)>/
  List<String> userEmailList = new ArrayList<>()
  for(email in emailLog){
    userEmailList << email[1]
  }
  println "printing userEmailList: "
  println userEmailList
  return userEmailList
}
