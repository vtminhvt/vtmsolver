definition(
    name: "Có chuyển động quạy chạy",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    
    section ("Thời gian quạt chạy")
        {
         input name: "timeofP", type: "number", title: "Đèn sáng trong bao lâu(phút)?", defaultValue:"1"
        }

    section("Chọn công tắc")
        {
            input("sw1","capability.switch",title:"Công tắc")
        }
    
    section("Cảm biến chuyển động")
    {
        input("motionCD", "capability.motionSensor",title:"Chọn CB Chuyển động")
    }
    //
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
	subscribe(motionCD,"motion",motion_CD)
  	subscribe(sw1,"switch",sw_1) 
}

def motion_CD(evt)
{
    if (evt.value == "active" && sel == "on")
    {
            sw1.on()
            def timeP=timeofP*60
            runIn(timeP,lightOFF)
    }
}

def lightOFF()
{
def t=motionCD.currentValue("motion")
    if(t=="active") 
    {
    	def timeP=timeofP*60	
    	runIn(timeP,lightOFF)    	
    }
    else
    {
    	sw1.off()
    }   
}