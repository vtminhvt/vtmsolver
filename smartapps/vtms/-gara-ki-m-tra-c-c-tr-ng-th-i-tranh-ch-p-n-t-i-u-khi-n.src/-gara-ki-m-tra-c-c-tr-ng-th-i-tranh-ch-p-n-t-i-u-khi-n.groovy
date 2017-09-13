definition(
    name: "[Gara]Kiểm tra các trạng thái tranh chấp nút điều khiển",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Trước khi thực hiện lệnh cần xem xét trạng thái hoạt động các nút bấm còn lại của gara, cửa cuốn",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
preferences 
{
 
  section("Nút ảo tác động")
    {
    	input("sw00","capability.switch",title:"")             
    }
  section("Nút thực tương đương nút ảo tác động")
    {
    	input("sw0","capability.switch",title:"")             
    }
    
     section("Nút còn lại phải dừng 1")
    {
    	input("sw1","capability.switch",title:"")             
    }
    
     section("Nút còn lại phải dừng 2")
    {
    	input("sw2","capability.switch",title:"")             
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
    subscribe(sw00,"switch",sw_00)    
    subscribe(sw0,"switch",sw_0)
    subscribe(sw1,"switch",sw_1)
    subscribe(sw2,"switch",sw_2)
  
}

def sw_00(evt)
{
if (evt.value=="on")
{
	sw0.on();	  
	sw1.off();
	sw2.off();
}
}