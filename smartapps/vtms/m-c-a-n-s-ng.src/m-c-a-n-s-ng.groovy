definition(
    name: "Mở cửa đèn sáng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//Test:OK

preferences 
{
    section("Kích hoạt hoạt động")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    section("Chọn khoảng thời gian")
    {
        input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu",defaultValue:"6:30PM"
        input name: "timeE", type: "time", title: "Đặt thời gian kết thúc", defaultValue:"4:30AM"
    }
    section ("Thời gian đèn sáng")
    {
       	input name: "timeofP", type: "number", title: "Đèn sáng bao lâu(phút)?", defaultValue:"1"
    }

    section("Chọn công tắc")
    {
    	   input("sw1","capability.switch",title:"Chọn công tắc")
    }
    section("Chọn cảm biến đóng mở")
    {
        input("cs1","capability.contactSensor",title:"Chọn cảm biến đóng mở")
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
	subscribe(cs1,"contact",cs_1) 
  	subscribe(sw1,"switch",sw_1) 
}
def cs_1(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC=now()
    def timeP=timeofP*60
    
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "open" && sel == "on")
    {
    
        if (dk1 || dk2)
        {
            sw1.on()
        }
    }
    if(evt.value=="closed")
    {
            sw1.off()
    }
}