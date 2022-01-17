package br.com.cursoamq;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidorTopicoEstoqueSelector {
	
	public static void main(String args[]) throws NamingException, JMSException {
		System.out.println("Iniciando TesteConsumidor Topico Estoque Selector...");
		//criando conexão com active mq
		InitialContext ct = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) ct.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.setClientID("estoque");
		connection.start();		
		
		Session session = connection.createSession(false/*sem transacao*/, Session.AUTO_ACKNOWLEDGE);
		Topic topico = (Topic) ct.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura",
				"ebook=false OR ebook is null", false);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {		
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("Recebendo msg: "+textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		});
		
		
		System.out.println("Pressione ENTER para finalizar");
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		ct.close();
		System.out.println("Fechando conexão com ActiveMQ");
	}
}
