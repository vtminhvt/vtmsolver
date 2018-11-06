definition(
        name: "|Nhiều|Hẹn giờ bật, tắt thiết bị",
        namespace: "VTMS",
        author: "Võ Thanh Minh",
        description: "Kịch bản điều khiển thiết bị",
        category: "Safety & Security",
        iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hẹn giờ", options: ["on","off"], defaulValue:"off"
    }
    section("Giờ hẹn")
    {
        input name: "timeB", type: "time", title: "Đặt thời gian bật thiết bị"
        input name: "timeE", type: "time", title: "Đặt thời gian tắt thiết bị"
    }
    section("Chọn công tắc điều khiển")
    {
        input("sw1","capability.switch",title:"Công tắc", multiple:true, required:true)

    }
}
def installed() 
{
	init()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
}

def updated() 
{
	init()
    unschedule()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
   	
}
def init()
{
  	subscribe(sw1,"switch",sw_1) 
}
def lightON()
{
//	def sw1 = switches.find { it.displayName == theName }
	sw1.on()
}

def lightOFF()
{
//	def sw1 = switches.find { it.displayName == theName }
sw1.off()
}