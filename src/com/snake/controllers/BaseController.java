package com.snake.controllers;

import com.snake.handlers.BaseHandler;

abstract class BaseController<THandler extends BaseHandler>  {
    protected THandler handler;
    protected BaseController(THandler handler){
        this.handler = handler;
    }
}
