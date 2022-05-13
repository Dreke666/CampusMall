package com.entity;

public class Goods {
    private int id;
    
    private String cover;

    private String name;

    private String intro;
    
    private String spec;
    
    private String content;
    
    private int price;

    private int stock;
    
    private int sales;
    
    private int typeId;
    
    private Types type;
    
    private boolean top;
    
    
	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

	public boolean getTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}
    
}