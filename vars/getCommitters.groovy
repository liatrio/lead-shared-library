def call() {


  def p = ['/bin/bash', '-c', git log -5 | cat].execute()
  p.waitFor()
  println p.text

  println "p above"



  def log = "git log -5".execute() | "cat".execute()
  log.waitFor()
  println "printing log below:"
  println log
  println "printing log.text below:"
  //println log.text
  def emailLog  = log.text =~ /<(.*@.*)>/
  List<String> userEmailList = new ArrayList<>()
  for(email in emailLog){
    userEmailList << email[1]
  }
  println "printing userEmailList: "
  println userEmailList
  return userEmailList
}
