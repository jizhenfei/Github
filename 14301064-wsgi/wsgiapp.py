import os

def app(environ, start_response):
    OK_status = '200 OK'
    Error_status = '404 Not found'
    
    response_headers = [('Content-Type', 'text/html')]
    
    site = environ['PATH_INFO'][1:]
    if 'html' in site:
        if os.path.exists(site):
            localpage = open(site, "r")
            lines = localpage.readlines()
            start_response(OK_status, response_headers)
            return lines
        else:
            start_response(Error_status, response_headers)
            return '<h1>404 Not found</h1>'
    else:
        return '<h1>Hello,%s!</h1>'%(environ['PATH_INFO'][1:])
    
