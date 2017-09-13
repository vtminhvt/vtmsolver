definition(
    name: "Mở cửa thì báo động",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Đặt giờ mà hệ thống kiểm tra mức độ an toàn",
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
    section("Kiểu báo động")
    {
        input name:"typ",type:"enum", title:"Kiểu báo động", options: ["A","L","AL"], defaultValue:"L"
    }
    section("Chọn khoảng thời gian báo động")
    {
        input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu"
        input name: "timeE", type: "time", title: "Đặt thời gian kết thúc"
    }

    section ("Thời gian báo động")
    {
        input name: "tp", type: "number", title: "Báo động trong bao lâu(giây)?", defaultValue:"15"
    }

    section("Chọn cảm biến khi phát hiện mở cửa")
    {
        input("cs1","capability.contactSensor",title:"Cảm biến đóng, mở")
    }	
    section("Chọn Báo động")
    {
        input("alamH","capability.alarm",title:"Báo động ở Nhà")
    }   
}
def installed()
{
	init() 
}
def updated() 
{ 
	init()
}

def init()
{
    subscribe(cs1,"contact",cs_1)
    subscribe(alamH,"alarm",alam_H)
}
def cs_1(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC=now()
    def p= tp*1000

	def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "open" && sel == "on")
    {
        if (dk1 || dk2)
		{	
        	if(typ=="L") 
            {
        		sendPush("Báo động do phát hiện cửa bị mở")
           		alamH.strobe()
        		schedule(now()+p,alamF) // turn off in 10 second
        	}
            if(typ=="A")
            {
        		sendPush("Báo động do phát hiện cửa bị mở")
        		alamH.siren()
        		schedule(now()+p,alamF) // turn off in 10 second
        	}
        	if(typ=="AL")
        	{
        		sendPush("Báo động do phát hiện cửa bị mở")
        		alamH.both()
           		schedule(now()+p,alamF) // turn off in 10 second
        	}
		}
	}
 }


def alamF()
{
	alamH.off()
}