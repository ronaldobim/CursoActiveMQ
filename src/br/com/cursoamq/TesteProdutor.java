package br.com.cursoamq;

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

public class TesteProdutor {
	
	public static void main(String args[]) throws NamingException, JMSException {
		System.out.println("Iniciando TesteProdutor...");
		//criando conexão com active mq
		InitialContext ct = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) ct.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();		
		
		Session session = connection.createSession(false/*sem transacao*/, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) ct.lookup("financeiro");
		
		MessageProducer producer = session.createProducer(fila);
		Message message = session.createTextMessage("Prdutor criando mensagem, pode ser JSON ou XML");
		producer.send(message);
		
		System.out.println("Pressione ENTER para finalizar");
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		ct.close();
		System.out.println("Fechando conexão com ActiveMQ");
	}
}
