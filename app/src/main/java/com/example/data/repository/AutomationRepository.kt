package com.example.data.repository

import com.example.data.models.DarkNiche
import com.example.data.models.DarkVideoDraft
import com.example.data.models.TutorialChapter
import com.example.data.models.WorkflowStep

class AutomationRepository {

    val checkoutUrl = "https://pay.cakto.com.br/38vofpw_1249382"
    val demoVideoUrl = "https://www.youtube.com/watch?v=iwxtDl8Jl3E&t=2s"
    val demoVideoId = "iwxtDl8Jl3E"

    val niches = listOf(
        DarkNiche(
            id = "misterios",
            name = "Mistérios & Fatos Ocultos",
            description = "Casos não resolvidos, teorias da conspiração, segredos históricos e enigmas universais.",
            iconName = "Psychology",
            avgCpm = "R$ 18 - R$ 35",
            viralPotential = "Extremamente Alto (Shorts & Longs)",
            defaultKeywords = listOf("misterio", "fatos desconhecidos", "segredo", "enigmas", "historia oculta")
        ),
        DarkNiche(
            id = "financas",
            name = "Finanças & Mente Milionária",
            description = "Hábitos de ricos, histórias de negócios, psicologia do dinheiro e investimentos.",
            iconName = "AttachMoney",
            avgCpm = "R$ 45 - R$ 90",
            viralPotential = "Alto (Público Qualificado)",
            defaultKeywords = listOf("financas", "riqueza", "mindset", "investimentos", "habitos de sucesso")
        ),
        DarkNiche(
            id = "curiosidades",
            name = "Ciência & Curiosidades Rápidas",
            description = "Fatos bizarros sobre o corpo, espaço, tecnologia do futuro e natureza inacreditável.",
            iconName = "Science",
            avgCpm = "R$ 14 - R$ 26",
            viralPotential = "Viralidade Máxima (Shorts / Reels)",
            defaultKeywords = listOf("curiosidades", "fatos bizarros", "ciencia", "voce sabia", "espaco")
        ),
        DarkNiche(
            id = "estoicismo",
            name = "Estoicismo & Disciplina",
            description = "Filosofia prática, citações impactantes, controle emocional e mentalidade blindada.",
            iconName = "SelfImprovement",
            avgCpm = "R$ 22 - R$ 42",
            viralPotential = "Muito Alto (Engajamento e Retenção)",
            defaultKeywords = listOf("estoicismo", "marco aurelio", "disciplina", "psicologia", "foco")
        ),
        DarkNiche(
            id = "geopolitica",
            name = "Geopolítica & Conflitos Mundiais",
            description = "Mapas 3D animados, disputas territoriais, poderio militar e estratégias de nações.",
            iconName = "Public",
            avgCpm = "R$ 35 - R$ 75",
            viralPotential = "Alto com Retenção Elevada",
            defaultKeywords = listOf("geopolitica", "guerra", "estrategia", "mapas", "historia mundial")
        )
    )

    val tutorialChapters = listOf(
        TutorialChapter(
            timeLabel = "00:02",
            title = "Apresentação da Máquina de Vídeos",
            description = "Como funciona o ecossistema 100% automático para canais dark sem expor seu rosto."
        ),
        TutorialChapter(
            timeLabel = "01:15",
            title = "Arquitetura do Fluxo no n8n / Make",
            description = "Demonstração dos nós integrados: gatilho de pauta, gerador de roteiro e renderização."
        ),
        TutorialChapter(
            timeLabel = "03:40",
            title = "Conexão com a YouTube Data API v3",
            description = "Autenticação das credenciais do Google Cloud Console e permissão de upload direto."
        ),
        TutorialChapter(
            timeLabel = "05:20",
            title = "Geração de Áudio Neural & B-Roll",
            description = "Sincronização da voz sintética hiper-realista com imagens cinematográficas dinâmicas."
        ),
        TutorialChapter(
            timeLabel = "08:10",
            title = "Publicação Agendada & Monetização",
            description = "Configuração do upload com tags otimizadas, descrição de SEO e agendamento automático."
        )
    )

    val workflowSteps = listOf(
        WorkflowStep(
            stepNumber = 1,
            title = "Trigger & Pesquisa de Tendência",
            tool = "RSS / YouTube Trends / Webhook",
            description = "Capta os tópicos com maior velocidade de busca no YouTube Brasil e Global nas últimas 24h.",
            samplePayload = """{"trigger": "trend_detected", "niche": "misterios", "topic": "Anomalia no fundo do Oceano"}"""
        ),
        WorkflowStep(
            stepNumber = 2,
            title = "Roteirização com Inteligência Artificial",
            tool = "Modelo de Linguagem com Prompt Magnético",
            description = "Estrutura o roteiro com fórmula de retenção: Gancho de 3 segundos + Curiosidade crescente + CTA final.",
            samplePayload = """{"hook": "Você não deveria ter visto isso...", "duration": "58s", "pacing": "dinamico"}"""
        ),
        WorkflowStep(
            stepNumber = 3,
            title = "Voz Neural & Sincronia de Legendas",
            tool = "API Text-To-Speech Neural",
            description = "Converte o texto em áudio profissional com tom documental imersivo e gera arquivo de legendas .SRT.",
            samplePayload = """{"voice_id": "documentary_pt_br", "stability": 0.85, "generate_subtitles": true}"""
        ),
        WorkflowStep(
            stepNumber = 4,
            title = "Criação de Cenas & Renderização de Vídeo",
            tool = "Motor de Renderização Automatizado",
            description = "Aplica zoom dinâmico, efeitos sonoros (SFX de suspense) e cortes a cada 2.5 segundos para reter 90%+.",
            samplePayload = """{"resolution": "1080x1920", "sfx_enabled": true, "cinematic_zoom": true}"""
        ),
        WorkflowStep(
            stepNumber = 5,
            title = "Upload Direto via YouTube Data API v3",
            tool = "Google Cloud YouTube API v3",
            description = "Envia o arquivo finalizado diretamente para o canal, aplicando título viral, miniatura e tags ranqueadas.",
            samplePayload = """{"privacy": "public", "category_id": "27", "made_for_kids": false}"""
        )
    )

    fun generateDraftForNiche(niche: DarkNiche, customTopic: String): DarkVideoDraft {
        val topic = if (customTopic.isNotBlank()) customTopic else "O Maior Segredo Nunca Revelado"
        return when (niche.id) {
            "misterios" -> DarkVideoDraft(
                title = "O Que Eles Encontraram Aqui Mudou a História Para Sempre",
                hook = "Se você pesquisar estas coordenadas exatas agora, o Google vai esconder os resultados. Preste muita atenção nos próximos 30 segundos...",
                script = """
[00:00 - GANCHO]
Se você pesquisar estas coordenadas exatas agora, o Google vai esconder os resultados. Preste muita atenção nos próximos 30 segundos.

[00:08 - O FATO]
Em outubro de 2018, uma equipe de exploração geológica no Ártico perfurou uma camada de gelo intacta há mais de 400 mil anos. O que os sensores detectaram abaixo não era rocha, nem gás natural. Era uma frequência pulsante e metálica constante de exatamente 432 Hz.

[00:26 - TENSÃO & PROVAS]
Dias depois, o governo local isolou uma área de 120 quilômetros. Todas as fotos tiradas pela equipe foram confiscadas e os cientistas transferidos para postos diferentes no mundo. No entanto, um deles conseguiu salvar um áudio de 14 segundos antes dos servidores serem apagados.

[00:45 - CLÍMAX & CTA]
Ouça com fones de ouvido... O que você acha que estava enterrado lá? Comente sua teoria e inscreva-se para a parte 2 antes que este vídeo seja retirado.
                """.trimIndent(),
                visualPrompts = listOf(
                    "Cinematic dark aerial view of snowy arctic landscape with eerie red glowing crevasse, photorealistic, 8k",
                    "Close up of glowing scientific radar screen showing frequency anomaly, dark laboratory lighting",
                    "Classified government file stamped with red CONFIDENTIAL wax seal on distressed dark wooden desk",
                    "Silhouette of lone scientist looking down into mysterious abyss under ice, volumetric blue neon light"
                ),
                tags = listOf("fatos misteriosos", "segredos da terra", "arquivos confidenciais", "curiosidades", "misterio sem explicacao"),
                description = "Descubra o evento oculto no gelo que mobilizou equipes em segredo. Inscreva-se no canal para mais investigações diárias sem aparecer.\n\n#misterio #curiosidades #fatos #historia",
                durationSeconds = 58,
                niche = niche.name
            )
            "financas" -> DarkVideoDraft(
                title = "A Regra dos 10 Segundos Que os Ricos Usam Para Nunca Ficar Pobres",
                hook = "Os bilionários não ficaram ricos investindo no que você pensa. Eles usam uma regra silenciosa que a escola nunca te ensinou...",
                script = """
[00:00 - GANCHO]
Os bilionários não ficaram ricos investindo no que você pensa. Eles usam uma regra silenciosa de 10 segundos que a escola nunca te ensinou.

[00:08 - A REVELAÇÃO]
Toda vez que você recebe dinheiro, seu cérebro busca conforto imediato: roupas, delivery, assinaturas que você nem usa. Mas o 1% faz o oposto. Nos primeiros 10 segundos ao ver o saldo na conta, 30% do valor é imediatamente direcionado para ativos geradores de fluxo.

[00:26 - A MATEMÁTICA BRUTAL]
Se você investir R$ 15 por dia a 12% ao ano, em 10 anos você terá acumulado mais de R$ 100.000 apenas em juros compostos trabalhando enquanto você dorme. A diferença entre quem ganha e quem perde é apenas a disciplina dos primeiros segundos.

[00:45 - O DESAFIO]
A partir de hoje: pague-se primeiro, antes de pagar qualquer conta. Qual hábito você vai cortar hoje? Deixe nos comentários e inscreva-se.
                """.trimIndent(),
                visualPrompts = listOf(
                    "Dramatic cinematic shot of luxury obsidian desk with gold coins and glowing green stock chart rising, 8k",
                    "Macro shot of mechanical luxury watch gear turning in slow motion, gold and dark titanium finish",
                    "Dark aesthetic modern glass skyscraper looking up from street level, moody cinematic lighting",
                    "Glowing digital dollar sign made of neon green circuits on dark carbon fiber texture"
                ),
                tags = listOf("mente milionaria", "habitos de ricos", "financas pessoais", "investimentos", "independencia financeira"),
                description = "Aplique esta regra prática na sua rotina diária e construa renda passiva. Inscreva-se para dominar as finanças.\n\n#financas #riqueza #mindset #investimentos",
                durationSeconds = 55,
                niche = niche.name
            )
            else -> DarkVideoDraft(
                title = "3 Coisas Que Seu Cérebro Faz Enquanto Você Dorme (E Você Nem Desconfia)",
                hook = "Seu cérebro faz algo perturbador exatamente às 3 da manhã enquanto seu corpo está completamente paralisado...",
                script = """
[00:00 - GANCHO]
Seu cérebro faz algo perturbador exatamente às 3 da manhã enquanto você dorme. E se você soubesse disso, nunca mais dormiria igual.

[00:08 - FATO 1]
Primeiro: Durante a fase REM, seu tronco encefálico desliga totalmente seus músculos voluntários para impedir que você execute seus sonhos na vida real. Você fica temporariamente paralisado para se proteger.

[00:24 - FATO 2]
Segundo: O sistema glinfático é ativado. Seus neurônios literalmente encolhem 60% para que um líquido especial lave as toxinas e resíduos acumulados durante o dia.

[00:40 - FATO 3 & CONCLUSÃO]
Terceiro: Ele simula cenários de perigo extremo para te treinar contra ameaças reais do dia a dia. Você já acordou caindo de um penhasco? Comente 'EU' e inscreva-se!
                """.trimIndent(),
                visualPrompts = listOf(
                    "Glowing neural network inside human brain with electric blue synapses firing, dark background, 8k",
                    "Cinematic macro shot of deep sleeping human eye with microscopic neon dream visuals reflected",
                    "Ethereal bioluminescent waves washing over crystalline abstract structures representing mental detox",
                    "Surreal dark aesthetic floating clock bending in cosmic starry night"
                ),
                tags = listOf("curiosidades", "fatos sobre o corpo", "neurociencia", "voce sabia", "ciencia divertida"),
                description = "Descubra os processos invisíveis do sono humano que a ciência acabou de mapear. Inscreva-se para novos fatos diários!\n\n#curiosidades #ciencia #shorts #vocesabia",
                durationSeconds = 56,
                niche = niche.name
            )
        }
    }

    val readyWorkflowJson = """
{
  "name": "Canal Dark Automático - Pipeline YouTube API v3",
  "nodes": [
    {
      "parameters": {
        "rule": {
          "interval": [
            {
              "field": "hours",
              "hoursInterval": 6
            }
          ]
        }
      },
      "id": "trigger-cron",
      "name": "Cron Trigger Agendado",
      "type": "n8n-nodes-base.cron",
      "typeVersion": 1,
      "position": [240, 300]
    },
    {
      "parameters": {
        "method": "POST",
        "url": "https://api.openai.com/v1/chat/completions",
        "sendHeaders": true,
        "headerParameters": {
          "parameters": [
            {
              "name": "Authorization",
              "value": "Bearer {{${'$'}env.AI_KEY}}"
            }
          ]
        },
        "sendBody": true,
        "bodyParameters": {
          "parameters": [
            {
              "name": "model",
              "value": "gpt-4o-mini"
            },
            {
              "name": "messages",
              "value": "[{\"role\":\"system\",\"content\":\"Você é um roteirista de alta retenção para canais dark do YouTube.\"},{\"role\":\"user\",\"content\":\"Gere um roteiro magnético de 50s com gancho irresistível, 4 prompts de cena e 15 tags ranqueadas.\"}]"
            }
          ]
        }
      },
      "id": "generate-script",
      "name": "Gerador de Roteiro & Prompts",
      "type": "n8n-nodes-base.httpRequest",
      "typeVersion": 4.1,
      "position": [460, 300]
    },
    {
      "parameters": {
        "method": "POST",
        "url": "https://api.elevenlabs.io/v1/text-to-speech/DOCUMENTARY_VOICE_ID",
        "sendHeaders": true,
        "headerParameters": {
          "parameters": [
            {
              "name": "xi-api-key",
              "value": "={{${'$'}env.TTS_API_KEY}}"
            }
          ]
        },
        "sendBody": true,
        "bodyParameters": {
          "parameters": [
            {
              "name": "text",
              "value": "={{${'$'}node['Gerador de Roteiro & Prompts'].json.script_content}}"
            }
          ]
        }
      },
      "id": "generate-audio",
      "name": "Voz Sintética ElevenLabs",
      "type": "n8n-nodes-base.httpRequest",
      "typeVersion": 4.1,
      "position": [680, 300]
    },
    {
      "parameters": {
        "method": "POST",
        "url": "https://api.creatomate.com/v1/renders",
        "sendHeaders": true,
        "headerParameters": {
          "parameters": [
            {
              "name": "Authorization",
              "value": "Bearer {{${'$'}env.RENDER_KEY}}"
            }
          ]
        },
        "sendBody": true,
        "bodyParameters": {
          "parameters": [
            {
              "name": "template_id",
              "value": "dark_channel_kinetic_subtitles_v2"
            },
            {
              "name": "modifications",
              "value": "={{JSON.stringify({\"Audio\": ${'$'}node['Voz Sintética ElevenLabs'].json.audio_url, \"Subtitles\": ${'$'}node['Gerador de Roteiro & Prompts'].json.subtitles})}}"
            }
          ]
        }
      },
      "id": "render-video",
      "name": "Renderizador de Vídeo Automático",
      "type": "n8n-nodes-base.httpRequest",
      "typeVersion": 4.1,
      "position": [900, 300]
    },
    {
      "parameters": {
        "authentication": "oAuth2",
        "resource": "video",
        "operation": "upload",
        "title": "={{${'$'}node['Gerador de Roteiro & Prompts'].json.video_title}}",
        "description": "={{${'$'}node['Gerador de Roteiro & Prompts'].json.video_description}}",
        "categoryId": "27",
        "privacyStatus": "public",
        "tags": "={{${'$'}node['Gerador de Roteiro & Prompts'].json.video_tags.join(',')}}"
      },
      "id": "youtube-upload",
      "name": "YouTube API v3 Auto-Upload",
      "type": "n8n-nodes-base.youTube",
      "typeVersion": 1,
      "position": [1120, 300]
    }
  ],
  "connections": {
    "Cron Trigger Agendado": {
      "main": [[{"node": "Gerador de Roteiro & Prompts", "type": "main", "index": 0}]]
    },
    "Gerador de Roteiro & Prompts": {
      "main": [[{"node": "Voz Sintética ElevenLabs", "type": "main", "index": 0}]]
    },
    "Voz Sintética ElevenLabs": {
      "main": [[{"node": "Renderizador de Vídeo Automático", "type": "main", "index": 0}]]
    },
    "Renderizador de Vídeo Automático": {
      "main": [[{"node": "YouTube API v3 Auto-Upload", "type": "main", "index": 0}]]
    }
  }
}
    """.trimIndent()
}
