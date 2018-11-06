definition(
    name: "Khi rời khỏi nhà đèn, thiết bị tắt",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test: OK

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    section("Chọn công tắc")
    {
   		input("swCC","capability.switch",title:"Chọn đèn hoặc công tắc", multiple:true, required:true)
    }
    section("Cảm biến hiện diện")
    {
    	input("presence","capability.presenceSensor",title:"Cảm biến hiện diện")
       	input("iPhone","capability.presenceSensor",title:"iPhone của Bạn")
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
    subscribe(swCC,"switch",sw_CC)
        
    subscribe(presence,"presence",presence_)
    subscribe(iPhone,"presence",iPhone_)
}

def presence_(evt)
{    
	if (evt.value == "present")
	{
            
	}
    else
      if (sel == "on")
    {
    		swCC.off()
			           
    }
}

def iPhone(evt)
{
	if (evt.value == "present")
	{
            
	}
    else
      if (sel == "on")
    {
    		swCC.off()
				
    }
}