package com.entity;

public class Carts {
    private int id;

    private int amount;

    private int goodId;

    private int userId;
    
    private int total;
    
    private Goods good;
    

    public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}