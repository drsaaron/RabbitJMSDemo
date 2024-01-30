#!/usr/bin/env python3

import pika
import json

# see https://www.rabbitmq.com/tutorials/tutorial-three-python.html

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

queue_name = 'jms-demo-consumer-2'

print(' [*] Waiting for logs. To exit press CTRL+C')

def callback(ch, method, properties, body):
    message = json.loads(body)
    print(f"object = {message}")

channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()
