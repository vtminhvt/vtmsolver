definition(
    name: "Báo thức",
    namespace: "VTMS",
    author: "Võ Thanh MInh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
	
section("Kích hoạt kịch bản")
{
	input name:"chon",type:"enum", title:"Hẹn giờ", options: ["on","off"], defaulValue:"off"
}
section("Giờ hẹn ")
    {
     input name: "timeCB", type: "time", title: "Đặt thời gian"
    }

 section ("Thời gian báo thức")
	{
     input name: "timeofP", type: "number", title: "Báo thức trong bao lâu(giây)?", defaultValue:"1"
    } 
    
section("Chọn thiết bị âm thanh")
    {
    	input("alamH","capability.alarm",title:"Loa báo động")
    }
}
def installed() 
{
	schedule(timeCB, cb)
   
    
    init()
}
def updated() 
{
	unschedule()
	schedule(timeCB, alamR)
   	init()	
}

def init()
{
  	subscribe(alamH,"alarm",alam_H) 
}
def initialize() 
{  
}

def alamR()
{
def timeP=timeofP*10000
if (chon=="on")
{
	sendPush("Đang báo thức bằng chuông báo động")
	alamH.both()
    schedule(now()+timeP,alamF)
 }
}

def alamF()
{
	alamH.off()
}