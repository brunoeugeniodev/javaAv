create table Cliente (
	idcliente serial not null primary key,
	nome varchar(100) not null,
	data_nascimento date not null,
	cpf varchar(14) not null
);

create table Venda (
	idvenda serial not null primary key,
	data_venda date not null,
	total_venda decimal not null,
	idcliente int,
	foreign key (idcliente) references Cliente(idcliente)
);

create table Produto (
	idproduto serial not null primary key,
	nome varchar(100) not null,
	marca varchar(60) not null,
	modelo varchar(50) not null,
	valor decimal not null
);

create table Venda_Produto (
	idvenda int,
	idproduto int,
	quantidade decimal not null,
	total_item decimal not null,
	desconto_intem decimal not null,
	foreign key (idvenda) references Venda(idvenda),
	foreign key (idproduto) references Produto(idproduto)
);

