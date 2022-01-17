package br.com.cursoamq;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidor {
	
	public static void main(String args[]) throws NamingException, JMSException {
		System.out.println("Iniciando TesteConsumidor...");
		//criando conex�o com active mq
		InitialContext ct = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) ct.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();		
		
		Session session = connection.createSession(false/*sem transacao*/, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) ct.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		Message message = consumer.receive();
		System.out.println("Recebendo msg: "+message);
		
		//System.out.println("Pressione ENTER para finalizar");
		//new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		ct.close();
		System.out.println("Fechando conex�o com ActiveMQ");
	}
}
