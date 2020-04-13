package com.invoice;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.frame.Dao;
import com.vo.InvoiceVO;
import com.vo.ItemVO;

@Service("invservice")
public class InvoiceService implements com.frame.Service<InvoiceVO> {

	@Resource(name="invdao")
	Dao<InvoiceVO> invdao;
	
	@Resource(name="itdao")
	Dao<ItemVO> itdao;
	
	@Override
	public void insert(InvoiceVO v) throws Exception {
		ItemVO updItem = new ItemVO();
		
		//item DB ������Ʈ�� ���� item id �޾ƿ�
		updItem.setItemid(v.getItemid());
		
		//��� ������ ���� ���� item DB�� �ִ� item ��� ���� ������
		ItemVO getItem = itdao.select(updItem);
		int tempstock = getItem.getItemstock();
			//�԰�
			if(v.getInvoicestat().equals("Receiving")) tempstock += v.getInvoiceqty();
			//���
			else if(v.getInvoicestat().equals("Shipping")) tempstock -= v.getInvoiceqty();
		updItem.setItemstock(tempstock);
		
		//invoice DB�� �� ������ �÷� ä����
		v.setWarename(getItem.getWarename());
		
		//item DB ������Ʈ
		itdao.update(updItem);
		
		//invoice DB ����� ���� insert
		invdao.insert(v);
	
	}

	@Override
	public void delete(InvoiceVO v) throws Exception {
		invdao.delete(v);
	}

	@Override
	public void update(InvoiceVO v) throws Exception {
		invdao.update(v);
	}

	@Override
	public InvoiceVO select(InvoiceVO v) throws Exception {
		return invdao.select(v);
	}

	@Override
	public ArrayList<InvoiceVO> selectAll(InvoiceVO v) throws Exception {
		
		return invdao.selectAll(v);
	}

}
