import logging
from datetime import datetime

def setup_logger(name, level=logging.INFO):
        
    """Function setup as many loggers as you want"""
    
    # Create a custom logger
    logger = logging.getLogger(name)
    
    # Set the log level
    logger.setLevel(level)
    
    # Create a handler that rotates every day
    log_file = datetime.now().strftime('log-rsi-%Y%m%d.log')
    file_handler = logging.FileHandler(log_file)
    
    # Set the log level for the file handler
    file_handler.setLevel(level)
    
    # Create a formatter and add it to the file handler
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    file_handler.setFormatter(formatter)
    
    # Add the file handler to the logger
    logger.addHandler(file_handler)
    
    return logger

logger = setup_logger('my_logger')
