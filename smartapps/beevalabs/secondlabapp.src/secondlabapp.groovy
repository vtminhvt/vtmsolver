include "asynchttp_v1"
 
 
definition(
 name: "secondlabapp",
 namespace: "beevalabs",
 author: "Beeva Labs",
 description: "first app",
 category: "SmartThings Labs",
 iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
 iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
 iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
 section("Sensor") {
 input "thesensor", "capability.motionSensor", required: true, title: "where?"
 }
}

def installed() {
 log.debug "Installed with settings: ${settings}"
 initialize()
}

def updated() {
 log.debug "Updated with settings: ${settings}"
 unsubscribe()
 initialize()
}

def initialize() {
 log.debug "to subcribe"
 log.debug subscribe(thesensor, "motion", senderAWS) 
 log.debug "subscribed"
 
 state.counter=0
 log.debug "Counter: ${state.counter}"
}

//event handlers

def sender(evt){
 log.debug "data received"
 state.counter=state.counter+1
 log.debug "Counter: ${state.counter}"
 log.debug "data to send: $evt"
 log.debug "Value to send: $evt.value"
 log.debug "Sender dev: $evt.Device"
}

def senderAWS(evt){
 def msg=""
 def urlComplete="http://sqs.eu-west-1.amazonaws.com/602636675831/smartthingsExperiment?Action=SendMessage&MessageBody=Smartthing+messages;device=${evt.deviceId};event=${evt.name};value=${evt.value};counter=${state.counter}"
 state.counter=state.counter+1
 log.debug(urlComplete)
 def params = [
 uri: urlComplete,
 path: msg,
 ]

try {
 httpGet(params) { resp ->
 resp.headers.each {
 log.debug "${it.name} : ${it.value}"
 }
 log.debug "response contentType: ${resp.contentType}"
 log.debug "response data: ${resp.data}"
 }

} catch (e) {
 log.error "something went wrong: $e"
 }
}

def processResponse(response, data) {
 if (response.hasError()) {
 try {
 log.debug "error http: $response.errorMessage"

} catch (e) {
 log.debug "error sending http post"
 log.debug "error http: $response.dump"
 }
 }else{
 log.debug "Post answers ${response} with data ${data}"
 }
}