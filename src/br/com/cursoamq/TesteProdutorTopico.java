package br.com.cursoamq;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorTopico {
	
	public static void main(String args[]) throws NamingException, JMSException {
		System.out.println("Iniciando TesteProdutorTopico...");
		//Usando classe Properties pra evitar o uso do arquivo jndi.properties
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("topic.loja", "topico.loja");
		
		//criando conexão com active mq
		InitialContext ct = new InitialContext(properties);
		ConnectionFactory factory = (ConnectionFactory) ct.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();		
		
		Session session = connection.createSession(false/*sem transacao*/, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) ct.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		for (int i = 1; i <= 10; i++) {
			Message message = session.createTextMessage("Mensagem topico número "+i);
			message.setBooleanProperty("ebook", true);
			if (i == 5) {
				message.setBooleanProperty("ebook", false);
			}
			producer.send(message);			
		}
		
		System.out.println("Pressione ENTER envio de mensagens para o topico");
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		ct.close();
		System.out.println("Fechando conexão com ActiveMQ");
	}
}
