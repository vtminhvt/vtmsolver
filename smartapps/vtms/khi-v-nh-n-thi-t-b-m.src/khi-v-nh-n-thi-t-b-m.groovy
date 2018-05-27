definition(
    name: "Khi về nhà đèn, thiết bị mở",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//Test: OK

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    section("Chọn khoảng thời gian")
        {
         input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu"
         input name: "timeE", type: "time", title: "Đặt thời gian kết thúc"
        }
    section ("Thời gian đèn sáng")
        {
         input name: "timeofP", type: "number", title: "Đèn sáng trong bao lâu(phút)?", defaultValue:"1"
        }

    section("Chọn công tắc")
        {
            input("sw1","capability.switch",title:"Công tắc",multiple:true, required:true)
        }
    
    section("Cảm biến hiện diện")
    {
    	input("presence1","capability.presenceSensor",title:"Cảm biến hiện diện")
      
    }
}
def installed() 
{
    init()
}
def updated() 
{
	unschedule()
   	init()	
}

def init()
{
	
  	subscribe(sw1,"switch",sw_1) 
	subscribe(presence1,"presence",presence_1)
   
}

def presence_1(evt)
{
	def timeofB = timeToday(timeB)
	def timeofE= timeToday(timeE)
	def timeC=now()
    
	 def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "present" && sel == "on")
    {
    
        if (dk1 || dk2)
		{
            sw1.on()
            def timeP=timeofP*60
            runIn(timeP,lightOFF)
		}
	}	
	else
	{
		//log.debug "timeP= ${timeP}"
	}
}

def lightOFF()
{
	sw1.off()
    //log.debug "Đèn đã tắt lightOFF()"
}