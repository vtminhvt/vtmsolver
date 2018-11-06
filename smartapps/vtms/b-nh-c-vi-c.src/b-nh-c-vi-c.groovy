definition(
    name: "B-Nhắc việc",
    namespace: "VTMS",
    author: "Vo Thanh Minh",
    description: "Kế hoạch công việc của từng cá nhân trong gia đình",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
	section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"", options: ["on","off"], defaulValue:"off"
    }
   
    section("TEXT")
    	{
        	input name:"txt",type:"text", title:"Lời nhắc",defaultValue:"Chưa nhập lời nhắc"
            input name:"dxt",type:"text", title:"Thời gian",defaultValue:"0 30 10 ? * FRI *"
            input name:"sxt",type:"text", title:"Số điện thoại",defaultValue:"+84"
    	}
}
def installed() 
{
schedule("${dxt}", cb)
       init()
}
def updated() 
{
	unschedule()
    schedule("${dxt}", cb)
	init()	
}

def init()
{ 
}
def initialize() {
	schedule("${dxt}", cb)
   }

def cb()
{
if (sel=="on")
{
	//sendPush("${txt}")
    sendSmsMessage("${sxt}", "${txt}");
}
}