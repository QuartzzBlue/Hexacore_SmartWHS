package com.invoicedetail;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.Dao;
import com.vo.InvoicedetailVO;

@Service("invdtlservice")
public class InvoicedetailService implements com.frame.Service<InvoicedetailVO> {

	@Resource(name="invdtldao")
	Dao<InvoicedetailVO> invdtldao;
	
//	@Resource(name="itdao")
//	Dao<ItemVO> itdao;
	
	
	@Transactional
	@Override
	public void insert(InvoicedetailVO v) throws Exception {
		
		
		/*������*/
//		ItemVO updItem = new ItemVO();
		
//		//item DB ������Ʈ�� ���� item id �޾ƿ�
//		updItem.setItemid(v.getItemid());
//		
//		//��� ������ ���� ���� item DB�� �ִ� item ��� ���� ������
//		ItemVO getItem = itdao.select(updItem);
//		int tempstock = getItem.getItemstock();
//			//�԰�
//			if(v.getInvoicestat().equals("Receiving")) tempstock += v.getInvoiceqty();
//			//���
//			else if(v.getInvoicestat().equals("Shipping")) tempstock -= v.getInvoiceqty();
//		updItem.setItemstock(tempstock);
//		
//		//invoice DB�� �� ������ �÷� ä����
//		v.setWarename(getItem.getWarename());
		
//		//item DB ������Ʈ
//		itdao.update(updItem);
		
		
		invdtldao.insert(v);
	}

	@Transactional
	@Override
	public void delete(InvoicedetailVO v) throws Exception {
		invdtldao.delete(v);
	}

	@Transactional
	@Override
	public void update(InvoicedetailVO v) throws Exception {
		invdtldao.update(v);
	}

	@Override
	public InvoicedetailVO select(InvoicedetailVO v) throws Exception {
		return invdtldao.select(v);
	}

	@Override
	public ArrayList<InvoicedetailVO> selectAll(InvoicedetailVO v) throws Exception {
		return invdtldao.selectAll(v);
	}

}
