definition(
    name: "[001-T]Có chuyển động đèn sáng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Tự động, điều khiển đèn khi có chuyển động PIR",
    category: "Convenience",
iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test: OK
preferences 
{
    section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản:", options: ["on","off"], defaultValue:"on"
        input name: "timeB", type: "time", title: "Chọn thời gian bắt đầu"
        input name: "timeE", type: "time", title: "Và thời gian kết thúc"
        input("motionCD", "capability.motionSensor",title:"Khi phát hiện chuyển động trên cảm biến:", multiple:true, required:true)
        input("sw1","capability.switch",title:"Thì công tắc sau sẽ sáng:", multiple:true, required:true)
        input name: "timeofP", type: "number", title: "Trong số phút:", defaultValue:"1"   
    }
}
def installed() 
	{
	subscribe(motionCD,"motion",motion_CD)
  	subscribe(sw1,"switch",sw_1) 
	}
def updated() 
	{
   	unsubscribe()
	subscribe(motionCD,"motion",motion_CD)
  	subscribe(sw1,"switch",sw_1) 
	}

def motion_CD(evt)
{
	def timeofB = timeToday(timeB)
	def timeofE= timeToday(timeE)
	def timeC=now()
	
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    def dk12= (dk1 || dk2)
    def dk3= (sel == "on")
  
  if (evt.value == "active") {
        if (dk12 && dk3 )
        	{
            sw1.on()
            def timeP=timeofP*60
            log.debug "${timeP} giá trị là"
            runIn(timeP,lightOFF)
            }
            else
            {
            log.debug "Không làm gì cả"
            }
        
		
        
     }	
}

def lightOFF()
{
   	sw1.off() 
}  
